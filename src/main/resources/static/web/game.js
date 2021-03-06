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
        var gp1 = myJson.gamePlayer[0].gp_id;
        var gp2 = myJson.gamePlayer[1].gp_id;
        if (myJson.gp_id == gp1 || myJson.gp_id == gp2) {
            myShips = myJson.ships;
            myGP = myJson.gamePlayer;
            hitTheOpp = myJson.hitTheOpp;
            mySalvoes = myJson.salvoes;
            myOpponentsSalvoes = myJson.opponentsSalvoes;
            markShips(myShips, "myTable");
            markShips(mySalvoes, "salvoTable");
            placeNewShip();
            placeSalvos();
            name(myShips, myOpponentsSalvoes, "ship", "myTable");
            name1(mySalvoes, hitTheOpp, "salvo", "salvoTable");
            getGP(myGP);
        }
    });

// get game players
function getGP(myGP) {
    var template = '';
    myGP.forEach(gp => {
        template += `
        <br>
        <p> ${gp.player.player_id.toString() === id ? "PLAYER:" : "VIEWER:"} ${gp.player.email} </p>
       `;
    });
    var email = document.getElementById('email');
    email.innerHTML = template;
}

// mark ships
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

// alert when clicking on a cell
//function ShipCellVal(cell) {
//    var row = document.getElementById('myTable');
//     var cells = row.getElementsByTagName('td');
//    alert(cells[0].getAttribute("id"));
//}

// place ships
function placeNewShip() {
    var tbl = document.getElementById('myTable');
    if (tbl != null) {
        for (var i = 0; i < tbl.rows.length; i++) {
            for (var j = 0; j < tbl.rows[i].cells.length; j++) {
                tbl.rows[i].cells[j].onclick = function () {
                    ShipCellVal(this);
                }
            }
        }
    }
}

// place salvos
function placeSalvos() {
    var table = document.querySelector('#salvoTable');
    var selectedCells = table.getElementsByClassName('selected')
    table.addEventListener('click', function (e) {
    var td = e.target
    if (td.tagName != 'TD') {
    return
    }
    if (selectedCells.length >= 5 ) {
      console.log(selectedCells[0].className)
      console.log("my loc is " + td.id)
    alert('you have exceeded 5 shots')
    } else {
//        if(!salvoLoc.includes(td.id)) {
            td.className = 'selected'
//            }
        }
    })
}

//salvo locations
var salvoList = []
function salvoLoc() {
    mySalvoes.forEach(salvo => {
        salvo.location.forEach(location => {
            salvoLoc.push(location);
        })
    })
}

// print ship locations
function name(me, opponent, someVar, table) {
    me.forEach(ship => {
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

 // print ship locations
function name1(me, salvo, someVar, table) {
    me.forEach(ship => {
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
     {
         type: "destroyer",
         locations: []
     },
     {
         type: "carrier",
         locations: []
     },
     {
         type: "submarine",
         locations: []
     },
     {
         type: "battleship",
         locations: []
     },
     {
         type: "patrolBoat",
         locations: []
     }
 ]

var shipsInfo = [
    {
        type: "destroyer",
        locations: [],
        direction: "horizontal",
        size: 2
    },
    {
        type: "carrier",
        locations: [],
        direction: "horizontal",
        size: 5
    },
    {
        type: "submarine",
        locations: [],
        direction: "horizontal",
        size: 3
    },
    {
        type: "battleship",
        locations: [],
        direction: "horizontal",
        size: 4
    },
    {
        type: "patrolBoat",
        locations: [],
        direction: "horizontal",
        size: 3
    }
]

function refresh() {
    location.reload();
}

function submit() {
//    check structure let gpid = data.current.gpid
    $.post({
            url: `localhost:8080/api/games/players/${gpid}/ships`,
            data: JSON.stringify({
                shipsList
            }),
            dataType: "text",
            contentType: "application/json"
        })
        .done(function (response, status, jqXHR) {
            alert("Ship added: " + response);
        })
        .fail(function (error) {
            alert("Failed to add ship: " + error);
        })
}

// select a ship
var selectedShip = ""
function selectShip(type) {
    document.getElementById('destroyer').style.backgroundColor = "deepskyblue";
    document.getElementById('carrier').style.backgroundColor = "deepskyblue";
    document.getElementById('battleship').style.backgroundColor = "deepskyblue";
    document.getElementById('submarine').style.backgroundColor = "deepskyblue";
    document.getElementById('patrolBoat').style.backgroundColor = "deepskyblue";
    document.getElementById(`${type}`).style.backgroundColor = "lawngreen";
    selectedShip = type;
}

// select direction
function direction(type) {
    document.getElementById(`${type}`).style.transform = "rotate(90deg)";
    shipsInfo.forEach(ship => {
    let checked = false;
        if (ship.type == type) {
            if (ship.direction == "horizontal") {
                ship.direction = "vertical"
                checked = true;
            } else if (!checked) {
                ship.direction = "horizontal";
            }
        }
    })
}

// drag and drop
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();

    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
    let list = [];
    let firstLoc = ev.target.id;
    console.log(selectedShip)
    let split = ev.target.id.split("");
    let letter = split[0];
    let number = firstLoc.match(/\d+/g).map(Number)[0]
    let newLoc = letter + number;
    list.push(newLoc);
    shipsInfo.forEach(ship => {
        if (ship.type == selectedShip) {
            console.log(ship.direction)
            console.log(ship.size)
            console.log(firstLoc)
            let newNumber = number;
            let newLetter = letter;
            for (i = 1; i<ship.size; i++) {
                if (ship.direction== "horizontal") {
                    newNumber++
                    newLoc = letter + newNumber
                    list.push(newLoc);
                } else if (ship.direction == "vertical") {
                    newLetter.substring(0, newLetter.length - 1) + String.fromCharCode(newLetter.charCodeAt(newLetter.length - 1) + 1)
                    newLoc = newLetter + number
                    list.push(newLoc);
                }
            }
            ship.locations = list;
            console.log(list);
            shipsList.forEach(newShip => {
                if (ship.type == newShip.type) {
                    newShip.locations = ship.locations
                }
            })
            console.log(shipsList);
        }
    })
}

//background
var colors = new Array(
  [62,35,255],
  [60,255,60],
  [255,35,98],
  [45,175,230],
  [255,0,255],
  [255,128,0]);

var step = 0;
//color table indices for:
// current color left
// next color left
// current color right
// next color right
var colorIndices = [0,1,2,3];

//transition speed
var gradientSpeed = 0.002;

function updateGradient()
{

//  if ( $===undefined ) return;

var c0_0 = colors[colorIndices[0]];
var c0_1 = colors[colorIndices[1]];
var c1_0 = colors[colorIndices[2]];
var c1_1 = colors[colorIndices[3]];

var istep = 1 - step;
var r1 = Math.round(istep * c0_0[0] + step * c0_1[0]);
var g1 = Math.round(istep * c0_0[1] + step * c0_1[1]);
var b1 = Math.round(istep * c0_0[2] + step * c0_1[2]);
var color1 = "rgb("+r1+","+g1+","+b1+")";

var r2 = Math.round(istep * c1_0[0] + step * c1_1[0]);
var g2 = Math.round(istep * c1_0[1] + step * c1_1[1]);
var b2 = Math.round(istep * c1_0[2] + step * c1_1[2]);
var color2 = "rgb("+r2+","+g2+","+b2+")";

 $('#gradient').css({
   background: "-webkit-gradient(linear, left top, right top, from("+color1+"), to("+color2+"))"}).css({
    background: "-moz-linear-gradient(left, "+color1+" 0%, "+color2+" 100%)"});

  step += gradientSpeed;
  if ( step >= 1 )
  {
    step %= 1;
    colorIndices[0] = colorIndices[1];
    colorIndices[2] = colorIndices[3];

    //pick two new target color indices
    //do not pick the same as the current one
    colorIndices[1] = ( colorIndices[1] + Math.floor( 1 + Math.random() * (colors.length - 1))) % colors.length;
    colorIndices[3] = ( colorIndices[3] + Math.floor( 1 + Math.random() * (colors.length - 1))) % colors.length;

  }
}

setInterval(updateGradient,10);
