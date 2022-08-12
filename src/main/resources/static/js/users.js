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
const updateUserEndpoint = "/arcadeit/admin/user/update"

function fetchUsersApi() {
    fetch(usersApiEndpoint).then(response => {
        return response.json();
    }).then(data => {
        Object.values(data).map(value => {
            let tr = document.createElement("tr");
            let id = 0;
            Object.keys(value).map(key => {
                let td = document.createElement("td");
                td.innerHTML = `${value[key]}`;
                id = value["id"];
                tr.appendChild(td);
            });
            let actionTdElement = document.createElement("td");
            // TODO: add style for this and change href
            actionTdElement.innerHTML = `<button class="update-user-button"><a href="${updateUserEndpoint + "/" + id}" class="">Update</a></button>
                                         <button class="delete-user-button"><a th:href="@{/admin/users-form}" class="">Delete</a></button>`;
            tr.appendChild(actionTdElement);
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