myShips = [];
mySalvoes = [];
hitTheOpp = [];
myOpponentsSalvoes = [];

let url = new URLSearchParams(window.location.search);
var id = url.get('gp');

fetch(`../api/game_view/${id}`)
    .then(function (response) {
        return response.json();
    })
    .then(function (myJson) {
//    console.log("hihi" + JSON.stringify(myJson))
    var gp1 = myJson.gamePlayer[0].gp_id;
    var gp2 = myJson.gamePlayer[1].gp_id;
        if (myJson.gp_id == gp1 || myJson.gp_id == gp2) {
        myShips = myJson.ships;
        myGP = myJson.gamePlayer;
        hitTheOpp = myJson.hitTheOpp;
        mySalvoes = myJson.salvoes;
        myOpponentsSalvoes = myJson.opponentsSalvoes;
        markShips(myShips, "myTable");
        placeNewShip()

        if (myShips.length < 5) {
            placedShips()
        } else {
            markShips(mySalvoes, "salvoTable");
        }
        name(myShips, myOpponentsSalvoes, "ship", "myTable");
        name1(mySalvoes, hitTheOpp, "salvo", "salvoTable");
        getGP(myGP);
        }
    });

function getGP(myGP) {
    var template = '';
    myGP.forEach(gp => {
        template += `
        <p> Username: ${gp.player.email} </p>
        <!-- if gp.player.id = url id, ?(display) "Player" :(else) "Viewer" -->
        <p> ${gp.player.player_id.toString() === id ? "(Player)" : "(Viewer)"} </p>
       `;
    });
   var email = document.getElementById('email');
   email.innerHTML = template;
}


function markShips(myShips, table) {

    row = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
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
}

 function placeNewShip() {
     var tbl = document.getElementById('myTable');
     console.log(tbl)
     if (tbl != null) {
     for (var i=0; i<tbl.rows.length; i++) {
        for(var j=0; j<tbl.rows[i].cells.length; j++){
            tbl.rows[i].cells[j].onclick = function () {
            cellVal(this);
            }
        }
     }
 }
 }

function cellVal(cell) {
     alert(cell.td);
}

function name(my, opponent, someVar, table) {
    // print ship location
    my.forEach(ship => {
        let checked = [];
        ship.location.forEach(location1 => {
            opponent.forEach(salvo => {
                salvo.location.forEach(location2 => {
                    if (!checked.includes((location1)) && !checked.includes(location2))
                        if (location1 == location2) {
                            document.getElementById(table).querySelector(`#${ location1 }`).classList.add("hit")
                        } else {
                            document.getElementById(table).querySelector(`#${ location1 }`).classList.add(someVar)
                        }

                })
            })
        })
    });
}

    function name1(my, salvo, someVar, table) {
        // print ship location
        my.forEach(ship => {
            let checked = [];
            ship.location.forEach(location1 => {
                    salvo.forEach(location2 => {
                        if (!checked.includes((location1)) && !checked.includes(location2))
                            if (location1 == location2) {
                                document.getElementById(table).querySelector(`#${ location1 }`).classList.add("hit")
                            } else {
                                document.getElementById(table).querySelector(`#${ location1 }`).classList.add(someVar)
                            }
                        })
                    })
                })

        };

var shipsList = [
{type: "destroyer", locations: []},
{type: "carrier", locations: []},
{type: "submarine", locations: []},
{type: "battleship", locations: []},
{type: "patrolBoat", locations: []}
]

function placedShips(gpid) {
$.post({
    url: `localhost:8080/api/games/players/${gpid}/ships`,
    data: JSON.stringify({
    shipsList
    }),
    dataType: "text",
    contentType: "application/json"
    })
    .done(function (response, status, jqXHR) {
      alert( "Ship added: " + response );
    })
    .fail(function (error) {
      alert("Failed to add ship: " + error);
    })
 }

 function selectShip(type) {
      document.getElementById('destroyer').style.backgroundColor = "deepskyblue";
      document.getElementById('carrier').style.backgroundColor = "deepskyblue";
      document.getElementById('battleship').style.backgroundColor = "deepskyblue";
      document.getElementById('submarine').style.backgroundColor = "deepskyblue";
      document.getElementById('patrolBoat').style.backgroundColor = "deepskyblue";
//      shipsList.forEach (ship => {
//      if (ship.location.length >= 2) {
//      document.getElementById(`${ship.type}`).style.backgroundColor = "green";
//      }
//      })
      document.getElementById(`${type}`).style.backgroundColor = "purple";
   }

 function direction(type) {
    document.getElementById('destroyer').style.transform = "rotate(0deg)";
    document.getElementById('carrier').style.transform = "rotate(0deg)";
    document.getElementById('battleship').style.transform = "rotate(0deg)";
    document.getElementById('submarine').style.transform = "rotate(0deg)";
    document.getElementById('patrolBoat').style.transform = "rotate(0deg)";

    document.getElementById(`${type}`).style.transform = "rotate(90deg)";
 }

