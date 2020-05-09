import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import {Navbar,  NavLink, Nav, Form, Button} from 'react-bootstrap';
import '../assets/navbar.css';


export default class NavigationBar extends Component{
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
                    <Button variant="outline-info" as={Link} to="/login" className="signButton">Login</Button>
                    <Button variant="outline-info" as={Link} to="/register">Register</Button>
                </Form>
            </Navbar>
        );
    }
}
