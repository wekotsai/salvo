
myShips = [];
fetch('http://localhost:8080/api/game_view/1')
    .then(function (response) {
        return response.json();
    })
    .then(function (myJson) {
        myShips = myJson.ships;
    });

window.onload = () => {

    // console.log("Ronnie: " + JSON.stringify(myShips))

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
                y.setAttribute("id",`${r}${c}`);
            }
        }
    }
}