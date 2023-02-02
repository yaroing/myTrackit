import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRequetePartenaire } from 'app/shared/model/requete-partenaire.model';
import { getEntities } from './requete-partenaire.reducer';

export const RequetePartenaire = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const requetePartenaireList = useAppSelector(state => state.mytrackit.requetePartenaire.entities);
  const loading = useAppSelector(state => state.mytrackit.requetePartenaire.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="requete-partenaire-heading" data-cy="RequetePartenaireHeading">
        <Translate contentKey="myTrackitApp.requetePartenaire.home.title">Requete Partenaires</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.requetePartenaire.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/requete-partenaire/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.requetePartenaire.home.createLabel">Create new Requete Partenaire</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {requetePartenaireList && requetePartenaireList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.requetePartenaire.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePartenaire.requeteDate">Requete Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePartenaire.fichierAtache">Fichier Atache</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePartenaire.requeteObs">Requete Obs</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePartenaire.reqTraitee">Req Traitee</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {requetePartenaireList.map((requetePartenaire, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/requete-partenaire/${requetePartenaire.id}`} color="link" size="sm">
                      {requetePartenaire.id}
                    </Button>
                  </td>
                  <td>
                    {requetePartenaire.requeteDate ? (
                      <TextFormat type="date" value={requetePartenaire.requeteDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {requetePartenaire.fichierAtache ? (
                      <div>
                        {requetePartenaire.fichierAtacheContentType ? (
                          <a onClick={openFile(requetePartenaire.fichierAtacheContentType, requetePartenaire.fichierAtache)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {requetePartenaire.fichierAtacheContentType}, {byteSize(requetePartenaire.fichierAtache)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{requetePartenaire.requeteObs}</td>
                  <td>{requetePartenaire.reqTraitee}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/requete-partenaire/${requetePartenaire.id}`}
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
                        to={`/requete-partenaire/${requetePartenaire.id}/edit`}
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
                        to={`/requete-partenaire/${requetePartenaire.id}/delete`}
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
              <Translate contentKey="myTrackitApp.requetePartenaire.home.notFound">No Requete Partenaires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RequetePartenaire;
