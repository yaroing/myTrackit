import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITypeAction } from 'app/shared/model/type-action.model';
import { getEntities } from './type-action.reducer';

export const TypeAction = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const typeActionList = useAppSelector(state => state.mytrackit.typeAction.entities);
  const loading = useAppSelector(state => state.mytrackit.typeAction.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="type-action-heading" data-cy="TypeActionHeading">
        <Translate contentKey="myTrackitApp.typeAction.home.title">Type Actions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.typeAction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/type-action/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.typeAction.home.createLabel">Create new Type Action</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {typeActionList && typeActionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.typeAction.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.typeAction.type">Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {typeActionList.map((typeAction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/type-action/${typeAction.id}`} color="link" size="sm">
                      {typeAction.id}
                    </Button>
                  </td>
                  <td>{typeAction.type}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/type-action/${typeAction.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/type-action/${typeAction.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/type-action/${typeAction.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myTrackitApp.typeAction.home.notFound">No Type Actions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TypeAction;
