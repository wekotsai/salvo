// getData();

// function getData() {
     fetch('../api/games')
         .then(response => response.json())
         .then(json => {
             console.log(json);
             let playerList = getPlayerList(json);
             let gameList = getGamesList(json);
             changeUrl(json);
             loggedInUser(json);
             displayScore(playerList);

 })
 // }

var errorStatus = false;

function login() {
    const email = document.querySelector("#inputEmail").value;
    console.log(email);
    const pw = document.querySelector("#inputPassword").value;
    console.log(pw);

    fetch("/api/login",
        {
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: "POST",
            body: "userName=" + email + "&password=" + pw
        })
        .then(function(res){
            console.log(res);
            location.reload();
        })
        .catch(function(res){ console.log(res) });

}

function loggedInUser(playerList) {
    if ( playerList.Current !== null) {
        document.getElementById("leaderboard").style.display = "block";
        document.getElementById("signIn").style.display = "none";
    }
}

function changeUrl(json) {
    var url = new URL('http://localhost:8080/web/games.html?');
    var query_string = url.search;
    var search_params = new URLSearchParams(query_string);

        search_params.append('gp', json.current.id);

        url.search = search_params.toString();
        var new_url = url.toString();
        console.log("new1 " + new_url);
        if (location.href !== new_url)
        {
        location.href= new_url;
        }
}


function register() {
    document.getElementById("regForm").style.display = "block";
}

function createGame() {
    let id;
    $.post("/api/games", {})
    .done(res => {
        console.log(res),
        id = res.gpid
        location.replace(`http://localhost:8080/web/game.html?gp=${id}`)
    })
    .fail(err => {errorMessage = err, console.log(errorMessage), alert("ERROR"), errorStatus = true})
 }


function joinGame() {
  $.post("/api/game/${id}/players", {})
    .done(res => {
        console.log(res),
        location.replace(`http://localhost:8080/web/game.html?gp=${id}`)
    })
    .fail(err => {errorMessage = err, console.log(errorMessage), alert("ERROR"), errorStatus = true})
 }


function signup() {

    var errorMessage = [];
    const regEmail = document.querySelector("#regEmail").value.toLowerCase();
    const regPw = document.querySelector("#regPassword").value;

   $.post("/api/players", {email: email, password: password})
   .done(res => login())
   .fail(err => {errorMessage = err, console.log(errorMessage), errorStatus = true})

    // fetch("/api/signup",
    //     {
    //         credentials: 'include',
    //         headers: {
    //             'Accept': 'application/json',
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         },
    //         method: "POST",
    //         body: "name" + name + "&userName=" + regEmail + "&password=" + regPw
    //     })
    //     .then(function(res){
    //         console.log(res);
    //     })
    //     .catch(function(res){ console.log(res) });
}

    function logout() {
        fetch("/api/logout",{
        }).then(function (res) {
            location.reload();
        })
            .catch(function(res){ console.log(res) });
        }

function getPlayerList(json) {
    console.log(JSON.stringify(json));
    var playerList = [];
    json.games.forEach(player => {

        let player_score = {};
        let wins = 0;
        let losses = 0;
        let ties = 0;
        let total_scores = 0;
        player.player.player_score.forEach(score => {
            if (score.game_score.scores == 1){
                return wins++;
            } else if (score.game_score.scores == 0.5){
                return ties++;
            } else {
                return losses++;
            }
        });
        total_scores = wins + (ties / 2);
        player_score = {"email": player.player.email,
                        "total_scores": total_scores,
                        "wins": wins,
                        "ties": ties,
                        "losses": losses};
        playerList.push(player_score);

    });

    return playerList;
}

function displayScore(playerList) {
   var templateTest = '';
    playerList.forEach(pl => {
             templateTest += `
             <tr>
             <td>${pl.email}</td>
             <td>${pl.total_scores}</td>
             <td>${pl.wins}</td>
             <td>${pl.ties}</td>
             <td>${pl.losses}</td>
             </tr>
             `;
             var table = document.getElementById('myTbody');
             table.innerHTML = templateTest;
         })
}

function getGamesList(json) {
   console.log("game" + JSON.stringify(json))
   var gameList = [];
   var templateTest = '';
    json.games.forEach(gm => {
             templateTest += `
             <tr>
             <td>${gm.games}</td>
             <td>${gm.email}</td>
             <td><button id="joinGame" class="btn btn-lg btn-primary text-uppercase" onclick="joinGame()">Join Game</button></td>
             </tr>
             `;
             var table = document.getElementById('gameTbody');
             table.innerHTML = templateTest;
         })

        return gameList
}

