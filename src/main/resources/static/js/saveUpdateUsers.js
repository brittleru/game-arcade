/**
 * Cache the DOM
 */
const usersForm = document.getElementById("users-form");
const userIdInput = document.getElementById("user-id");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const firstNameInput = document.getElementById("first-name");
const lastNameInput = document.getElementById("last-name");
const emailInput = document.getElementById("email");

// TODO: validate input from user for client side

/**
 * Rest API endpoints
 */
const usersApiEndpoint = "/arcadeit/api/v1/users";

function getRolesList() {
    let roles = [];
    let defaultRole;
    const inputs = document.querySelectorAll("input[type='checkbox']");
    inputs.forEach(inputElement => {
        if (inputElement.checked) {
            roles.push(generateRoleJson(inputElement));
        }
        if (trim("ROLE_".concat(inputElement.parentElement.innerText).toUpperCase()) === "ROLE_NORMAL") {
            defaultRole = generateRoleJson(inputElement);
        }
    });
    if (!roles.length) {
        roles.push(defaultRole);
    }
    console.log(roles);
    return roles;
}

function generateJsonForUser() {
    return {
        "id": trim(userIdInput.value),
        "username": trim(usernameInput.value),
        "password": trim(passwordInput.value),
        "firstName": trim(firstNameInput.value),
        "lastName": trim(lastNameInput.value),
        "email": trim(emailInput.value),
        "roles": getRolesList()
    };
}

function saveOrUpdateUser() {
    let userData = generateJsonForUser();
    console.log(userData);
    fetch(usersApiEndpoint, {
        method: "POST",
        headers: {
            "Accept": "*/*",
            "Content-type": "application/json"
        },
        body: JSON.stringify(userData)
    }).then(response => {
        return response.json();
    }).then(data => {
        console.log(data);
    }).catch(error => {
        console.log(error);
    });
}

// TODO: trim all the spaces from java, backend side
/**
 * Helper function to remove all the whitespaces from a string
 * @param str Given string
 * @returns {*} Cleaned from whitespaces string
 */
function trim(str) {
    return str.replace(/\s+/g, '');
}

/**
 * Helper function to generate an JSON representation of a role given its element
 * @param htmlElement Must be a checkbox input
 * @returns {{name, id}}
 */
function generateRoleJson(htmlElement) {
    let roleString = "ROLE_";
    return {
        "id": htmlElement.value,
        "name": trim(roleString.concat(htmlElement.parentElement.innerText).toUpperCase())
    };
}

usersForm.addEventListener("submit", saveOrUpdateUser);