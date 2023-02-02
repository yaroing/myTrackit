import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransporteur } from 'app/shared/model/transporteur.model';
import { getEntity, updateEntity, createEntity, reset } from './transporteur.reducer';

export const TransporteurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const transporteurEntity = useAppSelector(state => state.mytrackit.transporteur.entity);
  const loading = useAppSelector(state => state.mytrackit.transporteur.loading);
  const updating = useAppSelector(state => state.mytrackit.transporteur.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.transporteur.updateSuccess);

  const handleClose = () => {
    navigate('/transporteur');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...transporteurEntity,
      ...values,
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
          ...transporteurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.transporteur.home.createOrEditLabel" data-cy="TransporteurCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.transporteur.home.createOrEditLabel">Create or edit a Transporteur</Translate>
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
                  id="transporteur-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.transporteur.nomTransporteur')}
                id="transporteur-nomTransporteur"
                name="nomTransporteur"
                data-cy="nomTransporteur"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.transporteur.nomDirecteur')}
                id="transporteur-nomDirecteur"
                name="nomDirecteur"
                data-cy="nomDirecteur"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.transporteur.phoneTransporteur')}
                id="transporteur-phoneTransporteur"
                name="phoneTransporteur"
                data-cy="phoneTransporteur"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.transporteur.emailTransporteur')}
                id="transporteur-emailTransporteur"
                name="emailTransporteur"
                data-cy="emailTransporteur"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transporteur" replace color="info">
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

export default TransporteurUpdate;
