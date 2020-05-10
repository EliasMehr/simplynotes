import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import {Navbar,  NavLink, Nav, Form, Button} from 'react-bootstrap';
import '../assets/navbar.css';
import AccountService from "../services/AccountService";


export default class NavigationBar extends Component{

    constructor(props) {
        super(props);

        this.isLoggedIn = false;
        this.state = {
            user: AccountService.getCurrentUser(),
            isLoginSuccess : false
        }

    }

    componentDidMount() {

        if(this.state.user) {
            this.setState({
                isLoginSuccess: true
            })
        }

    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevState.isLoginSuccess !== this.state.isLoginSuccess) {
            console.log(this.state.isLoginSuccess);

        }
    }

    logOut() {
        AccountService.logout();
    }

    render() {


        if(this.state.isLoginSuccess) {
            return (
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand href="/">Spring Notes</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Item>
                                <Nav.Link as={Link} to="/">
                                    Home
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link as={Link} to="/notes">
                                    Notes
                                </Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Navbar.Collapse>
                    <Form inline>
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"#"} className="nav-link">
                                    {this.state.user.username}
                                </Link>
                            </li>
                            <li className="nav-item">
                                <a href="/login" className="nav-link" onClick={this.logOut}>
                                    LogOut
                                </a>
                            </li>
                        </div>

                    </Form>
                </Navbar>
            );
        }
        else {
            return (
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand href="/">Spring Notes</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Item>
                                <Nav.Link as={Link} to="/">
                                    Home
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link as={Link} to="/notes">
                                    Notes
                                </Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Navbar.Collapse>
                    <Form inline>
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/login"} className="nav-link">
                                    Login
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link to={"/register"} className="nav-link">
                                    Sign Up
                                </Link>
                            </li>
                        </div>


                    </Form>
                </Navbar>
            );
        }
    }
}
