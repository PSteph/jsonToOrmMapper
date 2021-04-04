var mymap = L.map('mapid').setView([3.84, 11.5], 15);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1Ijoic3RlcGhwcyIsImEiOiJjajAzdnczZ3cwMDV5MzJwZnNhb2d3ZmE5In0.jvEwP6GgM7q9wBxRbvLC8g', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1Ijoic3RlcGhwcyIsImEiOiJjajAzdnczZ3cwMDV5MzJwZnNhb2d3ZmE5In0.jvEwP6GgM7q9wBxRbvLC8g'
}).addTo(mymap);