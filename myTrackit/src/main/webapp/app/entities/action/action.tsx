import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAction } from 'app/shared/model/action.model';
import { getEntities } from './action.reducer';

export const Action = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const actionList = useAppSelector(state => state.mytrackit.action.entities);
  const loading = useAppSelector(state => state.mytrackit.action.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="action-heading" data-cy="ActionHeading">
        <Translate contentKey="myTrackitApp.action.home.title">Actions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.action.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/action/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.action.home.createLabel">Create new Action</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {actionList && actionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.action.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.action.dateAction">Date Action</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.action.rapportAction">Rapport Action</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.action.transfert">Transfert</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {actionList.map((action, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/action/${action.id}`} color="link" size="sm">
                      {action.id}
                    </Button>
                  </td>
                  <td>{action.dateAction ? <TextFormat type="date" value={action.dateAction} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{action.rapportAction}</td>
                  <td>{action.transfert ? <Link to={`/transfert/${action.transfert.id}`}>{action.transfert.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/action/${action.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/action/${action.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/action/${action.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myTrackitApp.action.home.notFound">No Actions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Action;
