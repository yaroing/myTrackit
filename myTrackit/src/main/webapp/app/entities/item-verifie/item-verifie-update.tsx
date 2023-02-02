import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMission } from 'app/shared/model/mission.model';
import { getEntities as getMissions } from 'app/entities/mission/mission.reducer';
import { IItemVerifie } from 'app/shared/model/item-verifie.model';
import { getEntity, updateEntity, createEntity, reset } from './item-verifie.reducer';

export const ItemVerifieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const missions = useAppSelector(state => state.mytrackit.mission.entities);
  const itemVerifieEntity = useAppSelector(state => state.mytrackit.itemVerifie.entity);
  const loading = useAppSelector(state => state.mytrackit.itemVerifie.loading);
  const updating = useAppSelector(state => state.mytrackit.itemVerifie.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.itemVerifie.updateSuccess);

  const handleClose = () => {
    navigate('/item-verifie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...itemVerifieEntity,
      ...values,
      mission: missions.find(it => it.id.toString() === values.mission.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...itemVerifieEntity,
          mission: itemVerifieEntity?.mission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.itemVerifie.home.createOrEditLabel" data-cy="ItemVerifieCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.itemVerifie.home.createOrEditLabel">Create or edit a ItemVerifie</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="item-verifie-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.itemVerifie.quantiteTransfert')}
                id="item-verifie-quantiteTransfert"
                name="quantiteTransfert"
                data-cy="quantiteTransfert"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemVerifie.quantiteRecu')}
                id="item-verifie-quantiteRecu"
                name="quantiteRecu"
                data-cy="quantiteRecu"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemVerifie.quantiteUtilisee')}
                id="item-verifie-quantiteUtilisee"
                name="quantiteUtilisee"
                data-cy="quantiteUtilisee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemVerifie.quantiteDisponible')}
                id="item-verifie-quantiteDisponible"
                name="quantiteDisponible"
                data-cy="quantiteDisponible"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.itemVerifie.quantiteEcart')}
                id="item-verifie-quantiteEcart"
                name="quantiteEcart"
                data-cy="quantiteEcart"
                type="text"
              />
              <ValidatedField
                id="item-verifie-mission"
                name="mission"
                data-cy="mission"
                label={translate('myTrackitApp.itemVerifie.mission')}
                type="select"
              >
                <option value="" key="0" />
                {missions
                  ? missions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item-verifie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ItemVerifieUpdate;
