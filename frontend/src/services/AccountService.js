import axios from 'axios';

const API_URL = "http://localhost:8080/"

class AccountService {

    registerUser(firstName, lastName, email, password, username) {
        return axios.post(API_URL + "api/auth/signup", {
            firstName,
            lastName,
            email,
            password,
            username
        });
    }

    logout() {
            localStorage.removeItem('user');
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));;
    }

    login(username, password) {

        return axios.post(API_URL + "api/auth/signin", {
            username,
            password
        })
            .then(res => {
                if(res.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(res.data));
                }

                return res.data;

            })
            .catch(err => {
                console.log(err.response)
            });
    }

}

export default new AccountService();