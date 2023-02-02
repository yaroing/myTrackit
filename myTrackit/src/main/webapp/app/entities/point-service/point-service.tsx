import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointService } from 'app/shared/model/point-service.model';
import { getEntities } from './point-service.reducer';

export const PointService = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const pointServiceList = useAppSelector(state => state.mytrackit.pointService.entities);
  const loading = useAppSelector(state => state.mytrackit.pointService.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="point-service-heading" data-cy="PointServiceHeading">
        <Translate contentKey="myTrackitApp.pointService.home.title">Point Services</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.pointService.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/point-service/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.pointService.home.createLabel">Create new Point Service</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pointServiceList && pointServiceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.pointService.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointService.nomPos">Nom Pos</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointService.posLon">Pos Lon</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointService.posLat">Pos Lat</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointService.posContact">Pos Contact</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointService.posGsm">Pos Gsm</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pointServiceList.map((pointService, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/point-service/${pointService.id}`} color="link" size="sm">
                      {pointService.id}
                    </Button>
                  </td>
                  <td>{pointService.nomPos}</td>
                  <td>{pointService.posLon}</td>
                  <td>{pointService.posLat}</td>
                  <td>{pointService.posContact}</td>
                  <td>{pointService.posGsm}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/point-service/${pointService.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/point-service/${pointService.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/point-service/${pointService.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myTrackitApp.pointService.home.notFound">No Point Services found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PointService;
