import React from "react";
import { Button, Col, Container } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { fetchUserTimesheets } from '../../../store/slices/timesheets';
import Moment from 'react-moment';
import ReactPaginate from 'react-paginate';
import moment from 'moment';
import { useHistory } from "react-router-dom";
import { fetchTimesheet } from "../../../store/slices/timesheet";
import { Modal } from 'react-bootstrap';
import { deleteTimesheet } from '../../../store/slices/timesheets';


const Table = styled.table`
border: 1px solid #2e2e2e;
border-bottom: none;
border-right: none;
border-left: none;
font-family: "Roboto", sans-serif;
background-color: #fff;
`;

const Header = styled.h2`
font-family: 'Roboto', sans-serif;
font-weight: 300;
font-size: 2rem;
letter-spacing: 0rem;
margin-bottom: 2rem;
`;


const IconYes = styled.i`
color: #26bf26;
transition: transform 0.2s;

&:hover{
    color: #2cde2c;
    transform: scale(1.1);
    cursor: pointer;
}
`;

const IconNo = styled.i`
color: #cc1d1f;
transition: transform 0.2s;

&:hover{
    color: #db2325;
    transform: scale(1.1);
    cursor: pointer;
}
`;


const Thead = styled.thead`
background: radial-gradient( circle farthest-corner at 12.3% 19.3%,  rgb(76, 78, 199) 0%, rgb(95, 118, 249) 100.2% );
color:white;
`;

export const TimesheetsPage = () => {


    const timesheets = useSelector(state => state.timesheets.timesheets.content);
    const page = useSelector(state => state.timesheets.timesheets);
    const dispatch = useDispatch();
    const history = useHistory();


    React.useEffect(() => {
        dispatch(fetchUserTimesheets({ cursor: 0 }));
    }, [dispatch]);

    const handlePageChange = e => {
        dispatch(fetchUserTimesheets({ cursor: e.selected }));
    }

    const handleClick = ({ id }) => {
        dispatch(fetchTimesheet({ id }));
        history.push(`/timesheet/view/${id}`);
    }

    const handleClickEdit = ({ id }) => {
        dispatch(fetchTimesheet({ id }));
        history.push(`/timesheet/edit/${id}`);
    }

    const [show, setShow] = React.useState(false);
    const [modalTimesheet, setModalTimesheet] = React.useState(null);

    const handleClose = () => setShow(false);
    const handleShow = (timesheet) => {
        setModalTimesheet(timesheet);
        setShow(true);
    }



    return (
        <Container className="mt-5">
            <Col className="d-flex justify-content-center">
                <Modal show={show} onHide={handleClose} className="mt-5">
                    <Modal.Header>
                        <Modal.Title style={{ margin: '0 auto' }}>Delete confirmation</Modal.Title>
                    </Modal.Header>
                    <Modal.Body style={{ display: "flex", height: "100%", justifyContent: "center", alignItems: "center" }}>
                        <h5 style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
                            <p style={{ justifyContent: "center", alignItems: "center", textAlign: 'center' }}>
                                Are you sure you want to <br></br> delete the timesheet for week <br></br> {moment(modalTimesheet?.from).format("DD/MM/YYYY")} - {moment(modalTimesheet?.to).format("DD/MM/YYYY")}?
                    </p>
                        </h5>
                    </Modal.Body>
                    <Modal.Footer>
                        <div style={{ display: "flex", width: "100%", justifyContent: "space-evenly" }}>
                            <IconYes className="fas fa-check-circle fa-3x" onClick={() => dispatch(deleteTimesheet({ id: modalTimesheet.id })) && handleClose()}></IconYes>
                            <IconNo className="fas fa-times-circle fa-3x" onClick={handleClose}></IconNo>
                        </div>
                    </Modal.Footer>
                </Modal>
                <Table className="table" style={{ width: '1200px' }}>
                    <thead style={{ height: '80px' }}>
                        <tr style={{ height: '80px' }}>
                            <th colSpan="11" className="h-100">
                                <div className="col-md-12 text-center">
                                    <Header>Your Timesheets:</Header>
                                </div>
                            </th>
                        </tr>
                    </thead>
                    <Thead>
                        <tr className="text-center">
                            <th scope="col" style={{ width: '560px' }}>Week</th>
                            <th scope="col" style={{ width: '140px' }}>Status</th>
                            <th scope="col" style={{ width: '140px' }}>Options</th>
                        </tr>
                    </Thead>
                    <tbody className="text-center">
                        {timesheets && timesheets?.map(timesheet => (
                            <tr key={timesheet.id}>
                                <td>
                                    <div classname="mt-2">
                                        <span>Week <Moment format={"DD/MM/YYYY"}>{timesheet?.from}</Moment> - <Moment
                                            format={"DD/MM/YYYY"}>{timesheet?.to}</Moment></span>
                                    </div>
                                </td>
                                <td>
                                    <div className="mt-2">
                                        <span>{timesheet?.statusType === "SUBMITTED" ? "Submitted" : "Open"}</span>
                                    </div>
                                </td>
                                <td>
                                    <div style={{ display: "flex" }}>
                                        {timesheet?.statusType === "SUBMITTED" ?
                                            <Button variant="outline-dark" style={{ marginRight: "0.2rem" }}
                                                className="form-control" onClick={() => handleClick({ id: timesheet?.id })}>View</Button> :
                                            <Button variant="outline-dark" style={{ marginRight: "0.2rem" }}
                                                className="form-control" onClick={() => handleClickEdit({ id: timesheet?.id })}>Edit</Button>}
                                        <Button variant="outline-dark" className="form-control"
                                            disabled={timesheet?.statusType === "SUBMITTED"} onClick={() => handleShow(timesheet)}>Delete</Button>
                                    </div>
                                </td>
                            </tr>)
                        )}
                        <tr>
                            <td colSpan="3">
                                <div className="d-flex justify-content-center">
                                    {timesheets?.length > 0 && <ReactPaginate
                                        pageCount={page.totalPages}
                                        pageRangeDisplayed={page.totalPages}
                                        marginPagesDisplayed={0}
                                        onPageChange={(e) => handlePageChange(e)}
                                        breakClassName={'page-item'}
                                        breakLinkClassName={'page-link'}
                                        containerClassName={'pagination'}
                                        pageClassName={'page-item'}
                                        pageLinkClassName={'page-link'}
                                        previousClassName={'page-item'}
                                        previousLinkClassName={'page-link'}
                                        nextClassName={'page-item'}
                                        nextLinkClassName={'page-link'}
                                        activeClassName={'active'}
                                    ></ReactPaginate>}

                                    {timesheets?.length === 0 && <span>No timesheets found.</span>}
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </Table>
            </Col>
        </Container>
    )
}