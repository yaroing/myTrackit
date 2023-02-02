import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRequetePointService } from 'app/shared/model/requete-point-service.model';
import { getEntities } from './requete-point-service.reducer';

export const RequetePointService = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const requetePointServiceList = useAppSelector(state => state.mytrackit.requetePointService.entities);
  const loading = useAppSelector(state => state.mytrackit.requetePointService.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="requete-point-service-heading" data-cy="RequetePointServiceHeading">
        <Translate contentKey="myTrackitApp.requetePointService.home.title">Requete Point Services</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.requetePointService.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/requete-point-service/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.requetePointService.home.createLabel">Create new Requete Point Service</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {requetePointServiceList && requetePointServiceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.stockDisponible">Stock Disponible</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.quantDem">Quant Dem</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.quantTrs">Quant Trs</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.quantRec">Quant Rec</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.reqTraitee">Req Traitee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.dateReq">Date Req</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.dateRec">Date Rec</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.dateTransfert">Date Transfert</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.requetePointService.pointService">Point Service</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {requetePointServiceList.map((requetePointService, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/requete-point-service/${requetePointService.id}`} color="link" size="sm">
                      {requetePointService.id}
                    </Button>
                  </td>
                  <td>{requetePointService.stockDisponible}</td>
                  <td>{requetePointService.quantDem}</td>
                  <td>{requetePointService.quantTrs}</td>
                  <td>{requetePointService.quantRec}</td>
                  <td>{requetePointService.reqTraitee}</td>
                  <td>
                    {requetePointService.dateReq ? (
                      <TextFormat type="date" value={requetePointService.dateReq} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {requetePointService.dateRec ? (
                      <TextFormat type="date" value={requetePointService.dateRec} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {requetePointService.dateTransfert ? (
                      <TextFormat type="date" value={requetePointService.dateTransfert} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {requetePointService.pointService ? (
                      <Link to={`/point-service/${requetePointService.pointService.id}`}>{requetePointService.pointService.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/requete-point-service/${requetePointService.id}`}
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
                        to={`/requete-point-service/${requetePointService.id}/edit`}
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
                        to={`/requete-point-service/${requetePointService.id}/delete`}
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
              <Translate contentKey="myTrackitApp.requetePointService.home.notFound">No Requete Point Services found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RequetePointService;
