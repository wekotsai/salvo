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

        myShips = myJson.ships;
        myGP = myJson.gamePlayer;
        hitTheOpp = myJson.hitTheOpp;
        mySalvoes = myJson.salvoes;
        myOpponentsSalvoes = myJson.opponentsSalvoes;
        markShips(myShips, "myTable");
        markShips(mySalvoes, "salvoTable");
        name(myShips, myOpponentsSalvoes, "ship", "myTable");
        name1(mySalvoes, hitTheOpp, "salvo", "salvoTable");
        getGP(myGP);
    });

function getGP(myGP) {

    var template = '';
    myGP.forEach(gp => {
        template += `
        <p> Username: ${gp.player.email} </p>
        <!-- if gp.player.id = url id, ?(display) "Player" :(else) "Viewer" -->
        <p> ${ gp.player.id.toString() === id ? "(Player)" : " (Viewer)"} </p>
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


function name(my, opponent, shit, table) {
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
                            document.getElementById(table).querySelector(`#${ location1 }`).classList.add(shit)
                        }

                })
            })
        })
    });
}

    function name1(my, salvo, shit, table) {
        // print ship location
        my.forEach(ship => {
            let checked = [];
            ship.location.forEach(location1 => {
                    salvo.forEach(location2 => {
                        if (!checked.includes((location1)) && !checked.includes(location2))
                            if (location1 == location2) {
                                document.getElementById(table).querySelector(`#${ location1 }`).classList.add("hit")
                            } else {
                                document.getElementById(table).querySelector(`#${ location1 }`).classList.add(shit)
                            }
                        })
                    })
                })

        };

    // mySalvoes.forEach(salvo => {
    //     salvo.location.forEach(location2 => {
    //         document.getElementById(table).querySelector(`#${ location2 }`).classList.add("salvo")
    //     })
    // })
