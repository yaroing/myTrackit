import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStockPointService } from 'app/shared/model/stock-point-service.model';
import { getEntities } from './stock-point-service.reducer';

export const StockPointService = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const stockPointServiceList = useAppSelector(state => state.mytrackit.stockPointService.entities);
  const loading = useAppSelector(state => state.mytrackit.stockPointService.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="stock-point-service-heading" data-cy="StockPointServiceHeading">
        <Translate contentKey="myTrackitApp.stockPointService.home.title">Stock Point Services</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.stockPointService.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/stock-point-service/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.stockPointService.home.createLabel">Create new Stock Point Service</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {stockPointServiceList && stockPointServiceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.stockAnnee">Stock Annee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.stockMois">Stock Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.entreeMois">Entree Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.sortieMois">Sortie Mois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.stockFinmois">Stock Finmois</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.stockDebut">Stock Debut</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.stockPointService.pointService">Point Service</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {stockPointServiceList.map((stockPointService, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/stock-point-service/${stockPointService.id}`} color="link" size="sm">
                      {stockPointService.id}
                    </Button>
                  </td>
                  <td>{stockPointService.stockAnnee}</td>
                  <td>{stockPointService.stockMois}</td>
                  <td>{stockPointService.entreeMois}</td>
                  <td>{stockPointService.sortieMois}</td>
                  <td>{stockPointService.stockFinmois}</td>
                  <td>{stockPointService.stockDebut}</td>
                  <td>
                    {stockPointService.pointService ? (
                      <Link to={`/point-service/${stockPointService.pointService.id}`}>{stockPointService.pointService.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/stock-point-service/${stockPointService.id}`}
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
                        to={`/stock-point-service/${stockPointService.id}/edit`}
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
                        to={`/stock-point-service/${stockPointService.id}/delete`}
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
              <Translate contentKey="myTrackitApp.stockPointService.home.notFound">No Stock Point Services found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StockPointService;
