import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointFocalPointService } from 'app/shared/model/point-focal-point-service.model';
import { getEntities } from './point-focal-point-service.reducer';

export const PointFocalPointService = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const pointFocalPointServiceList = useAppSelector(state => state.mytrackit.pointFocalPointService.entities);
  const loading = useAppSelector(state => state.mytrackit.pointFocalPointService.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="point-focal-point-service-heading" data-cy="PointFocalPointServiceHeading">
        <Translate contentKey="myTrackitApp.pointFocalPointService.home.title">Point Focal Point Services</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.pointFocalPointService.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/point-focal-point-service/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.pointFocalPointService.home.createLabel">Create new Point Focal Point Service</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pointFocalPointServiceList && pointFocalPointServiceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.pointFocalPointService.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointFocalPointService.nomPf">Nom Pf</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointFocalPointService.fonctionPf">Fonction Pf</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointFocalPointService.gsmPf">Gsm Pf</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.pointFocalPointService.emailPf">Email Pf</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pointFocalPointServiceList.map((pointFocalPointService, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/point-focal-point-service/${pointFocalPointService.id}`} color="link" size="sm">
                      {pointFocalPointService.id}
                    </Button>
                  </td>
                  <td>{pointFocalPointService.nomPf}</td>
                  <td>{pointFocalPointService.fonctionPf}</td>
                  <td>{pointFocalPointService.gsmPf}</td>
                  <td>{pointFocalPointService.emailPf}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/point-focal-point-service/${pointFocalPointService.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/point-focal-point-service/${pointFocalPointService.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/point-focal-point-service/${pointFocalPointService.id}/delete`}
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
              <Translate contentKey="myTrackitApp.pointFocalPointService.home.notFound">No Point Focal Point Services found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PointFocalPointService;
