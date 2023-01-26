/**
 * Cache the DOM
 */
const profilePicture = document.getElementById("profile-pic");
// const imageForm = document.getElementById("image-form");
// const imageFile = document.getElementById("image-file");

/**
 * Rest API endpoints
 */
// const uploadPictureEndpoint = "/arcadeit/api/v1/users/upload";
const userPictureNameEndpoint = "/arcadeit/api/v1/profile/picture/profile-image/";
const getUserPictureEndpoint = "/arcadeit/api/v1/profile/picture/";
const getAuthenticatedUsernameEndpoint = "/arcadeit/api/v1/user/authenticated";
const actualURL = window.location.href;

async function useProfilePictureIfExistent() {
    // let username;
    let filename;

    // TODO: use this for later, to have the user image near the logout
    // await fetch(getAuthenticatedUsernameEndpoint, {method: "GET"}).then(response => {
    //     return response.text();
    // }).then(data => {
    //     username = data;
    // }).catch(error => {
    //     console.log(error);
    // });

    const username = actualURL.substring(actualURL.lastIndexOf('/') + 1)
    const endpoint = `${userPictureNameEndpoint}${username}`;

    await fetch(endpoint, {method: "GET"}).then(response => {
        return response.text();
    }).then(data => {
        filename = data;
    }).catch(error => {
        console.log(error);
    });

    if (filename.length === 0) {
        return username;
    }
    const userPictureEndpoint = `${getUserPictureEndpoint}${filename}`;

    await fetch(userPictureEndpoint, {method: "GET"}).then(response => {
        return response.arrayBuffer();
    }).then(buffer => {

        const base64 = btoa(
            new Uint8Array(buffer).reduce(
                (data, byte) => data + String.fromCharCode(byte), ''
            )
        );
        profilePicture.src = `data:image/${filename.split('.').pop()};base64,${base64}`;
    }).catch(error => {
        console.log(error);
    });

    return username;
}

useProfilePictureIfExistent().then(username => {
    console.log(username);
});