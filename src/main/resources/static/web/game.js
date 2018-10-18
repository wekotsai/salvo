function getHeadersHtml(data) {
    return "<tr><th></th>" + data.destination_addresses.map(function(dest) {
        return "<th>" + dest + "</th>";
    }).join("") + "</tr>";
}