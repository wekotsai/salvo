myShips = [];
mySalvoes = [];

let url = new URLSearchParams(window.location.search);
var id = url.get('gp');

fetch(`../api/game_view/${id}`)
    .then(function (response) {
        return response.json();
    })
    .then(function (myJson) {

        myShips = myJson.ships;
        printShips(myShips, "myTable", "ship");
        myGP = myJson.gamePlayer;
        getGP(myGP);
        mySalvoes = myJson.salvoes;
        printShips(mySalvoes, "salvoTable", "salvo");

    });

function getGP(myGP) {

    var template = '';
    myGP.forEach(gp => {
        template += `
        <p>Username: ${gp.player.email}</p>
        <!-- if gp.player.id = url id, ?(display) "Player" :(else) "Viewer" -->
        <p>${ gp.player.id.toString() === id ? "Player" : "Viewer"}</p>
       `;
    });
   var email = document.getElementById('email');
   email.innerHTML = template;
}

function printShips(myShips, table, tab) {

    row = ["","A", "B", "C", "D", "E","F","G","H","I","J"];
    col = ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

    for (var r = 0; r < row.length; r++) {
            var x = document.getElementById(table).insertRow(r);
            for (var c = 0; c < col.length; c++) {
                var y = x.insertCell(c);
                if (r === 0 || c === 0) {
                    y.innerHTML = row[r] + col[c];
                } else {
                    y.setAttribute("id", `${row[r]}${c}`);
                }
            }
    }

    myShips.forEach(ship => {
        ship.location.forEach(oneLocation => {
            document.getElementById(table).querySelector(`#${ oneLocation }`).classList.add(tab)
        })
    });
}