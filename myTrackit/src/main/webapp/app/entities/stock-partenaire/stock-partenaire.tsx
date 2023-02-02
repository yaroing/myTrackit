import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStockPartenaire } from 'app/shared/model/stock-partenaire.model';
import { getEntities } from './stock-partenaire.reducer';

export const StockPartenaire = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const stockPartenaireList = useAppSelector(state => state.mytrackit.stockPartenaire.entities);
  const loading = useAppSelector(state => state.mytrackit.stockPartenaire.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="stock-partenaire-heading" data-cy="StockPartenaireHeading">
        <Translate contentKey="myTrackitApp.stockPartenaire.home.title">Stock Partenaires</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.stockPartenaire.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/stock-partenaire/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.stockPartenaire.home.createLabel">Create new Stock Partenaire</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {stockPartenaireList && stockPartenaireList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.stockAnnee">Stock Annee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.stockMois">Stock Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.entreeMois">Entree Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.sortieMois">Sortie Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.stockFinmois">Stock Finmois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPartenaire.stockDebut">Stock Debut</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {stockPartenaireList.map((stockPartenaire, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/stock-partenaire/${stockPartenaire.id}`} color="link" size="sm">
                      {stockPartenaire.id}
                    </Button>
                  </td>
                  <td>{stockPartenaire.stockAnnee}</td>
                  <td>{stockPartenaire.stockMois}</td>
                  <td>{stockPartenaire.entreeMois}</td>
                  <td>{stockPartenaire.sortieMois}</td>
                  <td>{stockPartenaire.stockFinmois}</td>
                  <td>{stockPartenaire.stockDebut}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/stock-partenaire/${stockPartenaire.id}`}
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
                        to={`/stock-partenaire/${stockPartenaire.id}/edit`}
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
                        to={`/stock-partenaire/${stockPartenaire.id}/delete`}
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
              <Translate contentKey="myTrackitApp.stockPartenaire.home.notFound">No Stock Partenaires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StockPartenaire;
