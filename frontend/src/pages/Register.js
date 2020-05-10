import React, { Component } from 'react';
import {Form, Row, Col, Button} from "react-bootstrap/";
import axios from 'axios';
import "../assets/register.css";

const API_URL = "http://localhost:8080/";

export default class Register extends Component {

    constructor(props) {
        super(props);

        this.state =
            {
                firstName: "",
                lastName: "",
                username: "",
                email: "",
                password: "",

                success: '',
                message: []
            };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChangeFirstName = this.onChangeFirstName.bind(this);
        this.onChangeLastName = this.onChangeLastName.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);

    }



    handleSubmit(e)
    {
        e.preventDefault();

         axios.post(API_URL + "api/auth/signup", {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            password: this.state.password,
            username: this.state.username
        })
            .then(res => {
                console.log(res);
                console.log(res.data);
                console.log(res.status + " res")
            })
             // .catch(err => {
             //     this.setState({
             //         message: err.response.data.errors
             //     })
             //     console.log(this.state.message);
             // })


    }


    onChangeFirstName(e) {
        this.setState({
            firstName: e.target.value
        });
    }

    onChangeLastName(e) {
        this.setState({
            lastName: e.target.value
        })
    }

    onChangeEmail(e) {
        this.setState({
            email: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    onChangeUsername = (e) => {
        this.setState({
            username: e.target.value
        })
    }


    render() {
        return (


            <Form className="registerCenter" onSubmit={this.handleSubmit}>
                <Form.Group as={Row} controlId="formGroupError">
                    <Form.Label column>
                        {this.state.message &&
                        <h3 className="error"> {this.state.message} </h3> }
                    </Form.Label>
                </Form.Group>
                <Form.Group as={Row} controlId="formGroupFirst">
                    <Form.Label column>
                        First name
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="text" placeholder="First name" onChange={this.onChangeFirstName} value={this.state.firstName}/>
                    </Col>
                </Form.Group>
                <Form.Group as={Row} controlId="formGroupLast">
                    <Form.Label column>
                        Last name
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="text" placeholder="Last name" onChange={this.onChangeLastName} value={this.state.lastName}/>
                    </Col>
                </Form.Group>
                <Form.Group as={Row} controlId="formGroupUsername">
                    <Form.Label column>
                        Username:
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="text" placeholder="Username" onChange={this.onChangeUsername} value={this.state.username}/>
                    </Col>
                </Form.Group>
                <Form.Group as={Row} controlId="formGroupEmail">
                    <Form.Label column>
                        Email
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="email" placeholder="Email" onChange={this.onChangeEmail} value={this.state.email}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="formGroupPassword">
                    <Form.Label column>
                        Password
                    </Form.Label>
                    <Col sm={25}>
                        <Form.Control type="password" placeholder="Password" onChange={this.onChangePassword} value={this.state.password}/>
                    </Col>
                </Form.Group>


                <Button variant="primary" type="submit">
                    Register
                </Button>
            </Form>
        )
    }

}