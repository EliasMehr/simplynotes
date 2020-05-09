import React, { Component } from 'react';
import {Card, CardGroup, Container, Col, Row} from "react-bootstrap";
import * as Icon from 'react-bootstrap-icons';
import HeaderJumbo from "../components/HeaderJumbo";
import "../assets/home.css";

export default class Home extends Component {
    render() {
            return <>
                <HeaderJumbo class="testStyle"/>
                <Card>
                    <Card.Body>
                        <CardGroup>
                            <Card>
                                <Card.Body>
                                    <Card.Title>Johannes danielsson</Card.Title>
                                    <Card.Text>
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                            <Card>
                                <Card.Body>
                                    <Card.Title>Sign benim up</Card.Title>
                                    <Card.Text>
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                            <Card>
                                <Card.Body>
                                    <Card.Title>benim mehr fazli</Card.Title>
                                    <Card.Text>
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                        Lorem shitum, eksde, lorem pittum, Lorem shitum, eksde, lorem pittum,
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </CardGroup>
                    </Card.Body>
                </Card>
                <Container>
                    <Row className="justify-content-md-center">
                        <Col md="auto">
                            <p><Icon.Phone size="25px"/> 02020202</p>
                            <p><Icon.Envelope size="25px"/>BenimBenim@Benim.Benim</p>
                            <p><Icon.House size="25px"/>Benim 2, Benim 20202</p>
                        </Col>
                    </Row>
                </Container>
            </>
    }
}