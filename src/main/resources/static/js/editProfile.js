/**
 * Cache the DOM
 */
const imageForm = document.getElementById("image-form");
const imageFile = document.getElementById("image-file");
const newImage = document.getElementById("new-image");
const newImageText = document.getElementById("new-image-text");

/**
 * Rest API endpoints
 */
const actualUserPictureNameEndpoint = "/arcadeit/api/v1/profile/picture/profile-image/";
const actualUserPictureEndpoint = "/arcadeit/api/v1/profile/picture/";
const uploadImageApiEndpoint = "/arcadeit/api/v1/profile/picture/upload";

const editURL = window.location.href;

function uploadImage() {

    let formData = new FormData();
    formData.append("profileImage", imageFile.files[0]);

    fetch(uploadImageApiEndpoint, {
        method: "POST",
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok. \n" + response);
        }
        return response.json();
    }).then(response => {
        console.log(response);
        // maybe redirect
    }).catch(error => {
        console.log(error);
    });
}

function changeImage() {
    let formData = new FormData();
    formData.append("profileImage", imageFile.files[0]);

    if (formData.get("profileImage")) {
        console.log(formData.get("profileImage"));
        newImageText.innerHTML = "New profile picture";
        newImage.src = URL.createObjectURL(formData.get("profileImage"));
    }
}

async function populateProfilePictureIfExistent() {
    let filename;

    const username = editURL.substring(editURL.lastIndexOf('/') + 1)
    const endpoint = `${actualUserPictureNameEndpoint}${username}`;

    await fetch(endpoint, {method: "GET"}).then(response => {
        return response.text();
    }).then(data => {
        filename = data;
    }).catch(error => {
        console.log(error);
    });

    if (filename.length === 0) {
        return;
    }

    const userPictureEndpoint = `${actualUserPictureEndpoint}${filename}`;

    await fetch(userPictureEndpoint, {method: "GET"}).then(response => {
        return response.arrayBuffer();
    }).then(buffer => {

        const base64 = btoa(
            new Uint8Array(buffer).reduce(
                (data, byte) => data + String.fromCharCode(byte), ''
            )
        );
        newImage.src = `data:image/${filename.split('.').pop()};base64,${base64}`;
    }).catch(error => {
        console.log(error);
    });

}

imageForm.addEventListener("submit", uploadImage);
imageFile.addEventListener("input", changeImage);
populateProfilePictureIfExistent().then(() => {});