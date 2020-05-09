import React, {Component} from 'react';
import { Jumbotron , Container, Row, Button } from 'react-bootstrap';
import "../assets/headerJumbo.css";



export default class HeaderJumbo extends Component{
    render() {
        return (
            <Jumbotron fluid className="jumbo" >
                <div className="overlay"></div>
                <Container className="headerContainer">
                    <Row>
                       <h1>Simply Notes</h1>
                    </Row>
                    <Row>
                        <div className="mb-2">
                            <Button variant="primary" size="lg">
                                Login
                            </Button>{' '}
                            <Button variant="primary" size="lg">
                                Register
                            </Button>
                        </div>
                    </Row>
                </Container>
            </Jumbotron>
        );
    }
}