import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IItemTransfert } from 'app/shared/model/item-transfert.model';
import { getEntities } from './item-transfert.reducer';

export const ItemTransfert = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const itemTransfertList = useAppSelector(state => state.mytrackit.itemTransfert.entities);
  const loading = useAppSelector(state => state.mytrackit.itemTransfert.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="item-transfert-heading" data-cy="ItemTransfertHeading">
        <Translate contentKey="myTrackitApp.itemTransfert.home.title">Item Transferts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.itemTransfert.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/item-transfert/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.itemTransfert.home.createLabel">Create new Item Transfert</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {itemTransfertList && itemTransfertList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.roDate">Ro Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.matDesc">Mat Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.unit">Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.delQty">Del Qty</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.batch">Batch</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.bbDate">Bb Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.weight">Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.volume">Volume</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.recQty">Rec Qty</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.itemTransfert.transfert">Transfert</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {itemTransfertList.map((itemTransfert, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/item-transfert/${itemTransfert.id}`} color="link" size="sm">
                      {itemTransfert.id}
                    </Button>
                  </td>
                  <td>{itemTransfert.roDate ? <TextFormat type="date" value={itemTransfert.roDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{itemTransfert.matDesc}</td>
                  <td>{itemTransfert.unit}</td>
                  <td>{itemTransfert.delQty}</td>
                  <td>{itemTransfert.value}</td>
                  <td>{itemTransfert.batch}</td>
                  <td>{itemTransfert.bbDate ? <TextFormat type="date" value={itemTransfert.bbDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{itemTransfert.weight}</td>
                  <td>{itemTransfert.volume}</td>
                  <td>{itemTransfert.recQty}</td>
                  <td>
                    {itemTransfert.transfert ? (
                      <Link to={`/transfert/${itemTransfert.transfert.id}`}>{itemTransfert.transfert.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/item-transfert/${itemTransfert.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/item-transfert/${itemTransfert.id}/edit`}
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
                        to={`/item-transfert/${itemTransfert.id}/delete`}
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
              <Translate contentKey="myTrackitApp.itemTransfert.home.notFound">No Item Transferts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ItemTransfert;
