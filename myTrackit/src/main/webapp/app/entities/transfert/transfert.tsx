import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransfert } from 'app/shared/model/transfert.model';
import { getEntities } from './transfert.reducer';

export const Transfert = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const transfertList = useAppSelector(state => state.mytrackit.transfert.entities);
  const loading = useAppSelector(state => state.mytrackit.transfert.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="transfert-heading" data-cy="TransfertHeading">
        <Translate contentKey="myTrackitApp.transfert.home.title">Transferts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.transfert.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/transfert/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.transfert.home.createLabel">Create new Transfert</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {transfertList && transfertList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.transfert.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transfert.dateExp">Date Exp</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transfert.nomChauffeur">Nom Chauffeur</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transfert.dateRec">Date Rec</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.transfert.cphone">Cphone</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transfertList.map((transfert, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/transfert/${transfert.id}`} color="link" size="sm">
                      {transfert.id}
                    </Button>
                  </td>
                  <td>{transfert.dateExp ? <TextFormat type="date" value={transfert.dateExp} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{transfert.nomChauffeur}</td>
                  <td>{transfert.dateRec ? <TextFormat type="date" value={transfert.dateRec} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{transfert.cphone}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/transfert/${transfert.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/transfert/${transfert.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/transfert/${transfert.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myTrackitApp.transfert.home.notFound">No Transferts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Transfert;
