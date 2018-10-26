getData();

function getData() {
    var data;
     fetch('../api/games')
         .then(response => response.json())
         .then(json => {
             data = json;
             console.log(json);
             getPlayerList(json);
             displayScore(json);
 });
 };

var playerList = [];

function getPlayerList(json) {
    json.forEach(player => {
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
                        "Total Score": total_scores,
                        "Wins": wins,
                        "Ties": ties,
                        "Losses": losses};
        playerList.push(player_score);
        console.log(playerList);
    })
}

function displayScore(playerList) {

    var templateTest = '';
    playerList.forEach(player => {
             templateTest += `
             
             <p>${player.player.email}</p>
             <p>${playerList.total_scores} </p>
             <p>${playerList.wins} </p>
             <p>${playerList.ties} </p>
             <p>${playerList.losses}</p>
            
             `;
         });
    var games = document.getElementById('games');
    games.innerHTML = templateTest;
};
 // function getGames(json) {
 //
 //     var templateTest = '';
 //     json.forEach(game => {
 //              templateTest += `
 //              <ol>
 //              <li>Game: ${game.id}</li>
 //              <li>Date: ${game.created}</li>
 //              <li>Player: ${ game.gamePlayer[0] !== undefined ? game.gamePlayer[0].player.userName : "" }</li>
 //              <li>Player: ${ game.gamePlayer[1] !== undefined ? game.gamePlayer[1].player.userName : "" }</li>
 //              </ol>
 //              `;
 //          });
 //     var games = document.getElementById('games');
 //     games.innerHTML = templateTest;
 // };