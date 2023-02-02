import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMission } from 'app/shared/model/mission.model';
import { getEntities } from './mission.reducer';

export const Mission = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const missionList = useAppSelector(state => state.mytrackit.mission.entities);
  const loading = useAppSelector(state => state.mytrackit.mission.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="mission-heading" data-cy="MissionHeading">
        <Translate contentKey="myTrackitApp.mission.home.title">Missions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.mission.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/mission/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.mission.home.createLabel">Create new Mission</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {missionList && missionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.mission.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.dateMission">Date Mission</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.dateDebut">Date Debut</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.dateFin">Date Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.rapportMission">Rapport Mission</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.debutMission">Debut Mission</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.finMission">Fin Mission</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.field10">Field 10</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.mission.fin">Fin</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {missionList.map((mission, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/mission/${mission.id}`} color="link" size="sm">
                      {mission.id}
                    </Button>
                  </td>
                  <td>{mission.dateMission ? <TextFormat type="date" value={mission.dateMission} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{mission.dateDebut ? <TextFormat type="date" value={mission.dateDebut} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{mission.dateFin ? <TextFormat type="date" value={mission.dateFin} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {mission.rapportMission ? (
                      <div>
                        {mission.rapportMissionContentType ? (
                          <a onClick={openFile(mission.rapportMissionContentType, mission.rapportMission)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {mission.rapportMissionContentType}, {byteSize(mission.rapportMission)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{mission.debutMission ? <TextFormat type="date" value={mission.debutMission} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{mission.finMission ? <TextFormat type="date" value={mission.finMission} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{mission.field10}</td>
                  <td>{mission.fin}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/mission/${mission.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/mission/${mission.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/mission/${mission.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myTrackitApp.mission.home.notFound">No Missions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Mission;
