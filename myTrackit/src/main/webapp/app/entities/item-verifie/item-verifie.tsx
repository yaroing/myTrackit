import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IItemVerifie } from 'app/shared/model/item-verifie.model';
import { getEntities } from './item-verifie.reducer';

export const ItemVerifie = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const itemVerifieList = useAppSelector(state => state.mytrackit.itemVerifie.entities);
  const loading = useAppSelector(state => state.mytrackit.itemVerifie.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="item-verifie-heading" data-cy="ItemVerifieHeading">
        <Translate contentKey="myTrackitApp.itemVerifie.home.title">Item Verifies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.itemVerifie.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/item-verifie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.itemVerifie.home.createLabel">Create new Item Verifie</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {itemVerifieList && itemVerifieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.quantiteTransfert">Quantite Transfert</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.quantiteRecu">Quantite Recu</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.quantiteUtilisee">Quantite Utilisee</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.quantiteDisponible">Quantite Disponible</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.quantiteEcart">Quantite Ecart</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemVerifie.mission">Mission</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {itemVerifieList.map((itemVerifie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/item-verifie/${itemVerifie.id}`} color="link" size="sm">
                      {itemVerifie.id}
                    </Button>
                  </td>
                  <td>{itemVerifie.quantiteTransfert}</td>
                  <td>{itemVerifie.quantiteRecu}</td>
                  <td>{itemVerifie.quantiteUtilisee}</td>
                  <td>{itemVerifie.quantiteDisponible}</td>
                  <td>{itemVerifie.quantiteEcart}</td>
                  <td>{itemVerifie.mission ? <Link to={`/mission/${itemVerifie.mission.id}`}>{itemVerifie.mission.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/item-verifie/${itemVerifie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/item-verifie/${itemVerifie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/item-verifie/${itemVerifie.id}/delete`}
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
              <Translate contentKey="myTrackitApp.itemVerifie.home.notFound">No Item Verifies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ItemVerifie;
