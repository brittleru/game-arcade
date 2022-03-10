const usersApiEndpoint = "/arcadeit/api/v1/endpoints";
let endpoints = {};

fetch(usersApiEndpoint).then(response => {
    return response.json();
}).then(data => {
    Object.entries(data).map((key, value) => {
        // endpoints = {...endpoints, ...{`${key}`: value}};
    })
    console.log(endpoints)
}).catch(error => {
    console.log(error);
})

console.log(window.location.href);
console.log(endpoints);