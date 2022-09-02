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
const updateUserEndpoint = "/arcadeit/admin/user/update";
const deleteUserEndpoint = "/arcadeit/admin/user/delete";

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
                switch (value[key]) {
                    case value["roles"]:
                        value[key].forEach(element => {
                            let tempRole = element["name"].replace("ROLE_", "");
                            rolesString += `${capitalize(tempRole)} <br>`;
                        });
                        td.innerHTML = rolesString;
                        break;
                    case value["password"]:
                        td.innerHTML = "PROTECTED";
                        break;
                    case value["createdAt"]:
                        td.innerHTML = value[key] ? `${new Date(value[key])}` : "invalid";
                        break;
                    case value["updatedAt"]:
                        td.innerHTML = value[key] ? `${new Date(value[key])}` : "invalid";
                        break;
                    default:
                        td.innerHTML = `${value[key]}`;
                }
                id = value["id"];
                tr.appendChild(td);
            });
            let actionTdElement = document.createElement("td");
            // TODO: add style for this and change href
            actionTdElement.innerHTML = `<button class="update-user-button"><a href="${updateUserEndpoint + "/" + id}" class="">Update</a></button>
                                         <button class="delete-user-button"><a href="${deleteUserEndpoint + "/" + id}" class=""
                                            onclick="if (!confirm('Are you sure you want to delete this user with ID: ${id}')) return false"
                                         >Delete</a></button>`;
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