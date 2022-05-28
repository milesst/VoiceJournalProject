export let USER_INFO = {};

export function setUserInfo(user) {
    USER_INFO = {
        id: user.id,
        firstName: user.firstName,
        lastName: user.lastName,
        patronymic: user.patronymic
    }

    console.log(USER_INFO)
    sessionStorage.setItem('firstName', JSON.stringify(user.firstName));
    sessionStorage.setItem('lastName', JSON.stringify(user.lastName));
    sessionStorage.setItem('patronymic', JSON.stringify(user.patronymic));
    sessionStorage.setItem('id', JSON.stringify(user.id));
}

export function getUserInfo() {
    return {
        lastName: JSON.parse(sessionStorage.getItem('lastName')),
        firstName: JSON.parse(sessionStorage.getItem('firstName')),
        patronymic: JSON.parse(sessionStorage.getItem('patronymic')),
        id: JSON.parse(sessionStorage.getItem('id')),
    };
}