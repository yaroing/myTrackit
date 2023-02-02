import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransporteur } from 'app/shared/model/transporteur.model';
import { getEntities } from './transporteur.reducer';

export const Transporteur = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const transporteurList = useAppSelector(state => state.mytrackit.transporteur.entities);
  const loading = useAppSelector(state => state.mytrackit.transporteur.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="transporteur-heading" data-cy="TransporteurHeading">
        <Translate contentKey="myTrackitApp.transporteur.home.title">Transporteurs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.transporteur.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/transporteur/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.transporteur.home.createLabel">Create new Transporteur</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {transporteurList && transporteurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.transporteur.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transporteur.nomTransporteur">Nom Transporteur</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transporteur.nomDirecteur">Nom Directeur</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transporteur.phoneTransporteur">Phone Transporteur</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transporteur.emailTransporteur">Email Transporteur</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transporteurList.map((transporteur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/transporteur/${transporteur.id}`} color="link" size="sm">
                      {transporteur.id}
                    </Button>
                  </td>
                  <td>{transporteur.nomTransporteur}</td>
                  <td>{transporteur.nomDirecteur}</td>
                  <td>{transporteur.phoneTransporteur}</td>
                  <td>{transporteur.emailTransporteur}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/transporteur/${transporteur.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/transporteur/${transporteur.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/transporteur/${transporteur.id}/delete`}
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
              <Translate contentKey="myTrackitApp.transporteur.home.notFound">No Transporteurs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Transporteur;
