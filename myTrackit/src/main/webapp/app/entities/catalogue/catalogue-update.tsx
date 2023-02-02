import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICatalogue } from 'app/shared/model/catalogue.model';
import { getEntity, updateEntity, createEntity, reset } from './catalogue.reducer';

export const CatalogueUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const catalogueEntity = useAppSelector(state => state.mytrackit.catalogue.entity);
  const loading = useAppSelector(state => state.mytrackit.catalogue.loading);
  const updating = useAppSelector(state => state.mytrackit.catalogue.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.catalogue.updateSuccess);

  const handleClose = () => {
    navigate('/catalogue');
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
      ...catalogueEntity,
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
          ...catalogueEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.catalogue.home.createOrEditLabel" data-cy="CatalogueCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.catalogue.home.createOrEditLabel">Create or edit a Catalogue</Translate>
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
                  id="catalogue-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.catalogue.materialCode')}
                id="catalogue-materialCode"
                name="materialCode"
                data-cy="materialCode"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.catalogue.materialDesc')}
                id="catalogue-materialDesc"
                name="materialDesc"
                data-cy="materialDesc"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.catalogue.materialGroup')}
                id="catalogue-materialGroup"
                name="materialGroup"
                data-cy="materialGroup"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/catalogue" replace color="info">
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

export default CatalogueUpdate;
