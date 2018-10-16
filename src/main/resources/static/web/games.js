getData();

function getData() {
    var data;
     fetch('../api/games')
         .then(response => response.json())
         .then(json => {
             data = json;
             console.log(data);
             getGames(data);
 });
 };

 function getGames(data) {

          var templateTest = '';
          data.forEach(game => {
              templateTest += `
              <ol>
              <li>Game: ${game.id}</li>
              <li>Date: ${game.created}</li>
              <li>Player: ${ game.gamePlayer[0] !== undefined ? game.gamePlayer[0].player.userName : "" }</li>
              <li>Player: ${ game.gamePlayer[1] !== undefined ? game.gamePlayer[1].player.userName : "" }</li>
              </ol>
              `;
          });
     var games = document.getElementById('games');
     games.innerHTML = templateTest;
 };