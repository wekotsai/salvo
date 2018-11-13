// getData();

// function getData() {
     fetch('../api/games')
         .then(response => response.json())
         .then(json => {
             console.log(json);
             let playerList = getPlayerList(json);
             changeUrl(json);
             loggedInUser(json);
             displayScore(playerList);

 })
 // }

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
   // debugger;
    var url = new URL('http://localhost:8080/web/games.html?gp=');
    var query_string = url.search;
    var search_params = new URLSearchParams(query_string);

        search_params.append('id', json.current.id);

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

function signup() {
    const name = document.querySelector("#regName").value;
    const regEmail = document.querySelector("#regEmail").value;
    const regPw = document.querySelector("#regPassword").value;

    fetch("/api/signup",
        {
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: "POST",
            body: "name" + name + "&userName=" + regEmail + "&password=" + regPw
        })
        .then(function(res){
            console.log(res);
        })
        .catch(function(res){ console.log(res) });
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
        if (json.current.id == player.player.player_id) {

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
    }
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

