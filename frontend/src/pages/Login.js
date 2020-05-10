import React, { Component } from 'react'
import { Form, Button, Row, Col } from 'react-bootstrap';
import axios from 'axios';
import "../assets/login.css";
import AccountService from "../services/AccountService";

const API_URL = "http://localhost:8080/";

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
        this.handleSubmit = this.handleSubmit.bind(this);
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



    handleSubmit(e) {
        e.preventDefault();

        AccountService.login(this.state.username, this.state.password)
            .then(() => {
                this.props.history.push("/notes")
                window.location.reload();
            })
    }


    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                <Form.Group as={Row} controlId="formGroupFirst">
                    <Form.Label column>
                        Username:
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="text" placeholder="Username" onChange={this.onChangeUsername} value={this.state.username}/>
                    </Col>
                </Form.Group>
                <Form.Group as={Row} controlId="formGroupLast">
                    <Form.Label column>
                        Password:
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="password" placeholder="Password" onChange={this.onChangePassword} value={this.state.password}/>
                    </Col>
                </Form.Group>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>

        );
    }
}


export default Login