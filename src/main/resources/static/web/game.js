myShips = [];

let url = new URLSearchParams(window.location.search);
var id = url.get('gp');

fetch(`../api/game_view/${id}`)
    .then(function (response) {
        return response.json();
    })
    .then(function (myJson) {

        myShips = myJson.ships;
        printShips(myShips);
        console.log(myShips);
        myGP = myJson.gamePlayer;
        getGP(myGP);
        console.log(myGP);

    });

function getGP(myGP) {

    var template = '';
    myGP.forEach(gp => {
        template += `
        <p>Username: ${gp.player.email}</p>
        <p>${ gp.player.id.toString() === id ? "Player" : "Viewer"}</p>
       `;
    });
    console.log('weko: ' + id);
   var email = document.getElementById('email');
   email.innerHTML = template;
}

function printShips(myShips) {

    row = ["","A", "B", "C", "D", "E","F","G","H","I","J"];
    col = ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

    for (var r = 0; r < row.length; r++)
    {
        var x = document.getElementById('myTable').insertRow(r);
        for (var c = 0; c < col.length; c++)
        {
            var y =  x.insertCell(c);
            if (r === 0 || c === 0) {
                y.innerHTML= row[r] + col[c];
            } else {
                y.setAttribute("id",`${row[r]}${c}`);
            }
        }
    }
    myShips.forEach(ship => {
        ship.location.forEach(oneLocation => {
            document.querySelector(`#${ oneLocation }`).classList.add('ship')
        })
    });
}
