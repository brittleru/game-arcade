/**
 * Cache the DOM
 */
const usersTable = document.getElementById("users-table-id");
const usersTableBody = document.getElementById("users-table-body");

/**
 * Rest API endpoints
 */
const endpointsApiEndpoint = "/arcadeit/api/v1/endpoints";
const usersApiEndpoint = "/arcadeit/api/v1/users";
const usersBasicInfoApiEndpoint = "/arcadeit/api/v1/users-basic-info";

function fetchUsersApi() {
    fetch(usersApiEndpoint).then(response => {
        return response.json();
    }).then(data => {
        Object.values(data).map(value => {
            let tr = document.createElement("tr");
            Object.keys(value).map(key => {
                let td = document.createElement("td");
                td.innerHTML = `${value[key]}`;
                tr.appendChild(td);
            });
            usersTableBody.appendChild(tr);
        });
    }).catch(error => {
        console.log(error);
    });
}

function fetchUsersBasicApi() {
    fetch(usersBasicInfoApiEndpoint).then(response => {
        return response.json();
    }).then(data => {
        console.log(data);
    }).catch(error => {
        console.log(error);
    });
}

fetchUsersApi();
// fetchUsersBasicApi();