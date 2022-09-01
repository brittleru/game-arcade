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
                let rolesString = "";
                if (value["roles"] === value[key]) {
                    value[key].forEach(element => {
                        let tempRole = element["name"].replace("ROLE_", "");
                        rolesString += `${capitalize(tempRole)} <br>`;
                    });
                    td.innerHTML = rolesString;
                }
                else if (value["password"] === value[key]) {
                    td.innerHTML = "PROTECTED";
                }
                else if (value["createdAt"] === value[key] || value["updatedAt"] === value[key]) {
                    if (value[key]) {
                        td.innerHTML = `${new Date(value[key])}`;
                    }
                    else {
                        td.innerHTML = "invalid";
                    }

                }
                else {
                    td.innerHTML = `${value[key]}`;
                }
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

/**
 * Helper function to capitalize the given string. (e.g., transform string: "test" in "Test")
 * @param string The given string, doesn't matter if the letters are upper or lower cased.
 * @returns {string} The given string in capitalized form.
 */
function capitalize(string) {
    string = string.toLowerCase();
    return string.charAt(0).toUpperCase() + string.slice(1);
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