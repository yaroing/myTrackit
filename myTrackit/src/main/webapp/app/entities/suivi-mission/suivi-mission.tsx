import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISuiviMission } from 'app/shared/model/suivi-mission.model';
import { getEntities } from './suivi-mission.reducer';

export const SuiviMission = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const suiviMissionList = useAppSelector(state => state.mytrackit.suiviMission.entities);
  const loading = useAppSelector(state => state.mytrackit.suiviMission.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="suivi-mission-heading" data-cy="SuiviMissionHeading">
        <Translate contentKey="myTrackitApp.suiviMission.home.title">Suivi Missions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.suiviMission.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/suivi-mission/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.suiviMission.home.createLabel">Create new Suivi Mission</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {suiviMissionList && suiviMissionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.suiviMission.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.suiviMission.problemeConstate">Probleme Constate</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.suiviMission.actionRecommandee">Action Recommandee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.suiviMission.dateEcheance">Date Echeance</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {suiviMissionList.map((suiviMission, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/suivi-mission/${suiviMission.id}`} color="link" size="sm">
                      {suiviMission.id}
                    </Button>
                  </td>
                  <td>{suiviMission.problemeConstate}</td>
                  <td>{suiviMission.actionRecommandee}</td>
                  <td>{suiviMission.dateEcheance}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/suivi-mission/${suiviMission.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/suivi-mission/${suiviMission.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/suivi-mission/${suiviMission.id}/delete`}
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
              <Translate contentKey="myTrackitApp.suiviMission.home.notFound">No Suivi Missions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SuiviMission;
