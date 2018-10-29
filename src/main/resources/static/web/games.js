getData();

function getData() {
     fetch('../api/games')
         .then(response => response.json())
         .then(json => {
             console.log("Isa: " + json);

             let playerList = getPlayerList(json);
             console.log("Ronn: " + playerList);
             displayScore(playerList);
 })
 }

function getPlayerList(json) {
    var playerList = [];
    json.forEach(player => {
        console.log("Mike: " + JSON.stringify(player.player))
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
        console.log("TOTAL: " + JSON.stringify(playerList));
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

