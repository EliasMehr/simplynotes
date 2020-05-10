import React, { Component } from 'react'
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import "../assets/login.css";

class Login extends Component {

    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",

            isLoginSuccess: ""
        };


        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.handleSubmit = this.handleSubmit(this);
    }

    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }



    handleSubmit = () => {
        let username = this.state.username;
        let password = this.state.password;

        fetch("http://localhost:8080/login", {
            headers: {
                "Authorization": 'Basic ' + window.btoa(username + ":" + password)
            }
        }).then(resp => {
            console.log(resp);
            if (resp.ok) {
                this.setState({
                    isLoginSucces: true});
            } else {
                this.setState({isLoginSucces: false});
            }

            return resp.text();
        });
    }


    render() {
        return (
            <Form onSubmit={this.handleSubmit}
            >
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control type="email" placeholder="Enter email" onChange={this.onChangeUsername} value={this.state.username}/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" onChange={this.onChangePassword} value={this.state.password}/>
                </Form.Group>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>

        );
    }
}


export default Login