import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPartenaire } from 'app/shared/model/partenaire.model';
import { getEntities } from './partenaire.reducer';

export const Partenaire = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const partenaireList = useAppSelector(state => state.mytrackit.partenaire.entities);
  const loading = useAppSelector(state => state.mytrackit.partenaire.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="partenaire-heading" data-cy="PartenaireHeading">
        <Translate contentKey="myTrackitApp.partenaire.home.title">Partenaires</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.partenaire.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/partenaire/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.partenaire.home.createLabel">Create new Partenaire</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {partenaireList && partenaireList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.partenaire.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.partenaire.nomPartenaire">Nom Partenaire</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.partenaire.autreNom">Autre Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.partenaire.logPhone">Log Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.partenaire.emailPartenaire">Email Partenaire</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.partenaire.locPartenaire">Loc Partenaire</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {partenaireList.map((partenaire, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/partenaire/${partenaire.id}`} color="link" size="sm">
                      {partenaire.id}
                    </Button>
                  </td>
                  <td>{partenaire.nomPartenaire}</td>
                  <td>{partenaire.autreNom}</td>
                  <td>{partenaire.logPhone}</td>
                  <td>{partenaire.emailPartenaire}</td>
                  <td>{partenaire.locPartenaire}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/partenaire/${partenaire.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/partenaire/${partenaire.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/partenaire/${partenaire.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myTrackitApp.partenaire.home.notFound">No Partenaires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Partenaire;
