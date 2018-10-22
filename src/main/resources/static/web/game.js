row = ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ];
col = ["","A", "B", "C", "D", "E","F","G","H","I","J"];

for (var r = 0; r < row.length; r++)
{
    var x = document.getElementById('myTable').insertRow(r);
    for (var c = 0; c < col.length; c++)
    {
        var y =  x.insertCell(c);
        y.innerHTML= row[r] + col[c];
    }
}

