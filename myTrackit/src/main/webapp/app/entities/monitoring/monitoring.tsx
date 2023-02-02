import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMonitoring } from 'app/shared/model/monitoring.model';
import { getEntities } from './monitoring.reducer';

export const Monitoring = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const monitoringList = useAppSelector(state => state.mytrackit.monitoring.entities);
  const loading = useAppSelector(state => state.mytrackit.monitoring.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="monitoring-heading" data-cy="MonitoringHeading">
        <Translate contentKey="myTrackitApp.monitoring.home.title">Monitorings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.monitoring.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/monitoring/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.monitoring.home.createLabel">Create new Monitoring</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {monitoringList && monitoringList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeAnnee">Atpe Annee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeMois">Atpe Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeStock">Atpe Stock</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeDispo">Atpe Dispo</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeEndom">Atpe Endom</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpePerime">Atpe Perime</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeRupture">Atpe Rupture</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeNjour">Atpe Njour</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeMagasin">Atpe Magasin</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpePalette">Atpe Palette</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpePosition">Atpe Position</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeHauteur">Atpe Hauteur</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpePersonnel">Atpe Personnel</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeAdmission">Atpe Admission</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeSortie">Atpe Sortie</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeGueris">Atpe Gueris</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeAbandon">Atpe Abandon</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpePoids">Atpe Poids</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeTrasnsfert">Atpe Trasnsfert</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.atpeParent">Atpe Parent</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.monitoring.pointService">Point Service</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {monitoringList.map((monitoring, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/monitoring/${monitoring.id}`} color="link" size="sm">
                      {monitoring.id}
                    </Button>
                  </td>
                  <td>{monitoring.atpeAnnee}</td>
                  <td>{monitoring.atpeMois}</td>
                  <td>{monitoring.atpeStock}</td>
                  <td>{monitoring.atpeDispo}</td>
                  <td>{monitoring.atpeEndom}</td>
                  <td>{monitoring.atpePerime}</td>
                  <td>{monitoring.atpeRupture}</td>
                  <td>{monitoring.atpeNjour}</td>
                  <td>{monitoring.atpeMagasin}</td>
                  <td>{monitoring.atpePalette}</td>
                  <td>{monitoring.atpePosition}</td>
                  <td>{monitoring.atpeHauteur}</td>
                  <td>{monitoring.atpePersonnel}</td>
                  <td>{monitoring.atpeAdmission}</td>
                  <td>{monitoring.atpeSortie}</td>
                  <td>{monitoring.atpeGueris}</td>
                  <td>{monitoring.atpeAbandon}</td>
                  <td>{monitoring.atpePoids}</td>
                  <td>{monitoring.atpeTrasnsfert}</td>
                  <td>{monitoring.atpeParent}</td>
                  <td>
                    {monitoring.pointService ? (
                      <Link to={`/point-service/${monitoring.pointService.id}`}>{monitoring.pointService.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/monitoring/${monitoring.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/monitoring/${monitoring.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/monitoring/${monitoring.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myTrackitApp.monitoring.home.notFound">No Monitorings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Monitoring;
