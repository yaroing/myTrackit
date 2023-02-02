import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './section.reducer';

export const SectionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sectionEntity = useAppSelector(state => state.mytrackit.section.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sectionDetailsHeading">
          <Translate contentKey="myTrackitApp.section.detail.title">Section</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sectionEntity.id}</dd>
          <dt>
            <span id="sectionNom">
              <Translate contentKey="myTrackitApp.section.sectionNom">Section Nom</Translate>
            </span>
          </dt>
          <dd>{sectionEntity.sectionNom}</dd>
          <dt>
            <span id="chefSection">
              <Translate contentKey="myTrackitApp.section.chefSection">Chef Section</Translate>
            </span>
          </dt>
          <dd>{sectionEntity.chefSection}</dd>
          <dt>
            <span id="emailChef">
              <Translate contentKey="myTrackitApp.section.emailChef">Email Chef</Translate>
            </span>
          </dt>
          <dd>{sectionEntity.emailChef}</dd>
          <dt>
            <span id="phoneChef">
              <Translate contentKey="myTrackitApp.section.phoneChef">Phone Chef</Translate>
            </span>
          </dt>
          <dd>{sectionEntity.phoneChef}</dd>
        </dl>
        <Button tag={Link} to="/section" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/section/${sectionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SectionDetail;
