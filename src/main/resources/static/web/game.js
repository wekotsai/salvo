var array = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, "A", "B", "C", "D", "E","F","G","H","I","J"],
    tr;

array.forEach((v, i) => {
    var td = document.createElement('td');

    // if (!(i = isNaN))
    if (!(i % 10)) {
        tr = document.createElement('tr');
        document.getElementById('table0').appendChild(tr);
    }
    td.appendChild(document.createTextNode(v));
    tr.appendChild(td);
});

// function getColumnsHtml(row) {
//     return row.elements.map(function(element) {
//         return "<td>" + element.distance.text + "</td>";
//     }).join("")
// }

// $.ajax({
//     type: 'POST',
//     url: 'http://localhost:8080/api/game_view/{nn}',
//     data: 'url=' + window.location.toString()
// });