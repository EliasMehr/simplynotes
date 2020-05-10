import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import {Navbar,  NavLink, Nav, Form, Button} from 'react-bootstrap';
import '../assets/navbar.css';


export default class NavigationBar extends Component{
    constructor(props) {
        super(props);

        this.state = {
            currentUser: undefined
        };
    }


    componentDidMount() {
        const user = JSON.parse(localStorage.getItem('user'));

        if(user) {
            this.setState({
                currentUser: JSON.parse(localStorage.getItem('user'))
            })
        }


    }

    render() {

        return (
            <Navbar expand="lg" variant="dark" bg="dark">
                <Navbar.Brand href="/">Spring Notes</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Item>
                            <Nav.Link>
                                <Link to="/">Home</Link>
                            </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link>
                                <Link to="/notes">Notes</Link>
                            </Nav.Link>
                        </Nav.Item>
                    </Nav>
                </Navbar.Collapse>
                <Form inline>


                    {this.currentUser ? (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/profile"} className="nav-link">
                                    {this.currentUser.username}
                                </Link>
                            </li>
                            <li className="nav-item">
                                <a href="/login" className="nav-link" onClick={this.logOut}>
                                    LogOut
                                </a>
                            </li>
                        </div>
                    ) : (
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
                    )}

                </Form>
            </Navbar>
        );
    }
}
