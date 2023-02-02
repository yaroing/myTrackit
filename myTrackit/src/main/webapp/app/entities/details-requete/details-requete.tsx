import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDetailsRequete } from 'app/shared/model/details-requete.model';
import { getEntities } from './details-requete.reducer';

export const DetailsRequete = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const detailsRequeteList = useAppSelector(state => state.mytrackit.detailsRequete.entities);
  const loading = useAppSelector(state => state.mytrackit.detailsRequete.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="details-requete-heading" data-cy="DetailsRequeteHeading">
        <Translate contentKey="myTrackitApp.detailsRequete.home.title">Details Requetes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.detailsRequete.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/details-requete/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.detailsRequete.home.createLabel">Create new Details Requete</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {detailsRequeteList && detailsRequeteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.detailsRequete.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.detailsRequete.quantiteDemandee">Quantite Demandee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.detailsRequete.quantiteApprouvee">Quantite Approuvee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.detailsRequete.quantiteRecue">Quantite Recue</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.detailsRequete.itemObs">Item Obs</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.detailsRequete.requetePartenaire">Requete Partenaire</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {detailsRequeteList.map((detailsRequete, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/details-requete/${detailsRequete.id}`} color="link" size="sm">
                      {detailsRequete.id}
                    </Button>
                  </td>
                  <td>{detailsRequete.quantiteDemandee}</td>
                  <td>{detailsRequete.quantiteApprouvee}</td>
                  <td>{detailsRequete.quantiteRecue}</td>
                  <td>{detailsRequete.itemObs}</td>
                  <td>
                    {detailsRequete.requetePartenaire ? (
                      <Link to={`/requete-partenaire/${detailsRequete.requetePartenaire.id}`}>{detailsRequete.requetePartenaire.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/details-requete/${detailsRequete.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/details-requete/${detailsRequete.id}/edit`}
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
                        to={`/details-requete/${detailsRequete.id}/delete`}
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
              <Translate contentKey="myTrackitApp.detailsRequete.home.notFound">No Details Requetes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DetailsRequete;
