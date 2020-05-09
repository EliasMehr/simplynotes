import React, {Component} from 'react';
import {Container} from 'react-bootstrap';
import "../assets/layout.css";

export default class Layout extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Container fluid="false">
                {this.props.children}
            </Container>
        );
    }
}